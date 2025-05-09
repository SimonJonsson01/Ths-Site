import {
  LoginTokenResponse,
  ResponseEntity,
  ReviewDto,
  User,
  UserLogin,
} from "../types";

/*
 * VITE_THS_SITE_API_URL kommer att vara URL för koppling till backend (Spring Boot).
`${API_URL}/review`
*/
const API_URL = import.meta.env.VITE_THS_SITE_API_URL;

// Fetches ReviewDto in a List. PUBLIC ACCESS
export const fetchReviews = async (): Promise<ReviewDto[]> => {
  const response = await fetch(`${API_URL}/review`);
  const data: ReviewDto[] = await response.json();
  return data;
};

// Fetches JobDto in a List
/* export const fetchJobs = async (): Promise<JobDto[]> => {
  const response = await fetch(`${API_URL}/job`);
  const data: JobDto[] = await response.json();
  return data;
}; */

// Create a new Customer.
export const createNewCustomer = async (
  user: User
): Promise<ResponseEntity> => {
  console.log(user);
  const response = await fetch(`${API_URL}/auth/signup`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user),
  });
  if (response.ok) {
    const data = await response.json();
    return { status: response.status, data };
  } else {
    throw new Error(response.status.toString());
  }
};

// Login with UserLogin and get a JWT token in return.
export const loginUser = async (
  user: UserLogin
): Promise<LoginTokenResponse> => {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user),
  });
  if (response.ok) {
    const data = await response.json();

    return {
      status: response.status,
      token: data.token,
    };
  } else {
    console.log("--------");
    console.log(response.status);
    throw new Error(response.toString());
  }
};
