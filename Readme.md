# RideShare API

**Postman Collection**: https://www.postman.com/rideshare-4871/workspace/rideshare/request/43142161-bf803d0c-ac35-43b7-b6ad-e40303a35972?action=share&creator=43142161&ctx=documentation

## Authentication

### Register User
```
POST /api/v1/auth/register
```
Create new user account (PASSENGER or DRIVER)

### Login
```
POST /api/v1/auth/login
```
Get JWT token for authentication

## Passenger APIs

### Create Ride Request
```
POST /api/v1/rides
```
Request a new ride

### Get My Rides
```
GET /api/v1/user/rides
```
View all your rides

### Cancel Ride
```
POST /api/v1/rides/{rideId}/cancel
```
Cancel a ride request

## Driver APIs

### View Pending Requests
```
GET /api/v1/driver/rides/requests
```
See all available ride requests

### Accept Ride
```
POST /api/v1/driver/rides/{rideId}/accept
```
Accept a ride request

### Complete Ride
```
POST /api/v1/rides/{rideId}/complete
```
Mark ride as completed

## User Profile

### Get Current User
```
GET /api/v1/users/me
```
Get logged-in user details
