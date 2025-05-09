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

export interface ReviewDto {
  title: string;
  score: number;
  content: string;
  user: UserDto;
}
