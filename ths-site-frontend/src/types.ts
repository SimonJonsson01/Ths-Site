/* --Types related to User (Customer & Admin)-- */
export interface UserDto {
  firstName: string;
  lastName: string;
}

export interface UserLogin {
  email: string;
  password: string;
}

export interface UserData {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface User {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface ResponseEntity {
  status: number;
  data: UserLogin;
}

export interface LoginTokenResponse {
  status: number;
  token: string;
}

export interface TokenData {
  id: string;
  sub: string;
  name: string;
  userType: string;
  iat: number;
  exp: number;
  email: string;
  authorities: authority[];
}

interface authority {
  authority: string;
}

/* --Other types-- */

export interface ReviewDto {
  title: string;
  score: number;
  content: string;
  user: UserDto;
}

export interface JobDto {
  title: string;
  content: string;
  createdAt: Date
  user: UserDto;
  completed: boolean;
}

export interface Job {
  id: string;
  title: string;
  content: string;
  createdAt: Date
  user: UserDto;
  completed: boolean;
}

export interface JobSender {
  title: string;
  content: string;
  customerId: string;
}

export interface ReviewSender {
  title: string;
  content: string;
  score: number;
  customerId: string;
}