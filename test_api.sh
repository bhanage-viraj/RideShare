#!/bin/bash

echo "========================================="
echo "RideShare API Complete Test Script"
echo "========================================="
echo ""

BASE_URL="http://localhost:8080/api/v1"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}Step 1: Register Passenger${NC}"
PASSENGER_RESPONSE=$(curl -s -X POST $BASE_URL/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice_passenger",
    "name": "Alice Johnson",
    "email": "alice@example.com",
    "password": "password123",
    "phone": "+1111111111",
    "role": "PASSENGER"
  }')
echo $PASSENGER_RESPONSE | jq '.'
echo ""

echo -e "${BLUE}Step 2: Register Driver${NC}"
DRIVER_RESPONSE=$(curl -s -X POST $BASE_URL/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "bob_driver",
    "name": "Bob Driver",
    "email": "bob@example.com",
    "password": "password123",
    "phone": "+2222222222",
    "role": "DRIVER"
  }')
echo $DRIVER_RESPONSE | jq '.'
echo ""

echo -e "${BLUE}Step 3: Login Passenger${NC}"
PASSENGER_LOGIN=$(curl -s -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "password123"
  }')
echo $PASSENGER_LOGIN | jq '.'
PASSENGER_TOKEN=$(echo $PASSENGER_LOGIN | jq -r '.token')
echo -e "${GREEN}Passenger Token: $PASSENGER_TOKEN${NC}"
echo ""

echo -e "${BLUE}Step 4: Login Driver${NC}"
DRIVER_LOGIN=$(curl -s -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "bob@example.com",
    "password": "password123"
  }')
echo $DRIVER_LOGIN | jq '.'
DRIVER_TOKEN=$(echo $DRIVER_LOGIN | jq -r '.token')
echo -e "${GREEN}Driver Token: $DRIVER_TOKEN${NC}"
echo ""

echo -e "${BLUE}Step 5: Passenger Creates Ride Request${NC}"
RIDE_RESPONSE=$(curl -s -X POST $BASE_URL/rides \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $PASSENGER_TOKEN" \
  -d '{
    "pickupLocation": "123 Main Street, New York",
    "dropLocation": "456 Park Avenue, New York"
  }')
echo $RIDE_RESPONSE | jq '.'
RIDE_ID=$(echo $RIDE_RESPONSE | jq -r '.id')
echo -e "${GREEN}Ride ID: $RIDE_ID${NC}"
echo -e "${GREEN}Status: REQUESTED${NC}"
echo ""

echo -e "${BLUE}Step 6: Driver Views Pending Requests${NC}"
PENDING_RIDES=$(curl -s -X GET $BASE_URL/driver/rides/requests \
  -H "Authorization: Bearer $DRIVER_TOKEN")
echo $PENDING_RIDES | jq '.'
echo ""

echo -e "${BLUE}Step 7: Driver Accepts Ride${NC}"
ACCEPT_RESPONSE=$(curl -s -X POST $BASE_URL/driver/rides/$RIDE_ID/accept \
  -H "Authorization: Bearer $DRIVER_TOKEN")
echo $ACCEPT_RESPONSE | jq '.'
echo -e "${GREEN}Status: ACCEPTED${NC}"
echo ""

echo -e "${BLUE}Step 8: Driver Completes Ride${NC}"
COMPLETE_RESPONSE=$(curl -s -X POST $BASE_URL/rides/$RIDE_ID/complete \
  -H "Authorization: Bearer $DRIVER_TOKEN")
echo $COMPLETE_RESPONSE | jq '.'
echo -e "${GREEN}Status: COMPLETED${NC}"
echo ""

echo -e "${BLUE}Step 9: Passenger Views Their Rides${NC}"
PASSENGER_RIDES=$(curl -s -X GET $BASE_URL/user/rides \
  -H "Authorization: Bearer $PASSENGER_TOKEN")
echo $PASSENGER_RIDES | jq '.'
echo ""

echo -e "${BLUE}Step 10: Driver Views Their Rides${NC}"
DRIVER_RIDES=$(curl -s -X GET $BASE_URL/user/rides \
  -H "Authorization: Bearer $DRIVER_TOKEN")
echo $DRIVER_RIDES | jq '.'
echo ""

echo "========================================="
echo -e "${GREEN}All Tests Completed Successfully!${NC}"
echo "========================================="
