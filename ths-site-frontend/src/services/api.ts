import {
  Job,
  JobDto,
  JobSender,
  LoginTokenResponse,
  ResponseEntity,
  ReviewDto,
  ReviewSender,
  TokenData,
  User,
  UserLogin,
} from "../types";

/*
 * VITE_THS_SITE_API_URL will be the URL for access to the backend (Spring Boot).
 */
const API_URL = import.meta.env.VITE_THS_SITE_API_URL;

// ---Public access-requests---

// - Fetches ReviewDto in a List.
export const fetchReviews = async (): Promise<ReviewDto[]> => {
  const response = await fetch(`${API_URL}/review`);
  const data: ReviewDto[] = await response.json();
  return data;
};

// - Create a new Customer.
export const createNewCustomer = async (
  user: User
): Promise<ResponseEntity> => {
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

// - Login with UserLogin and get a JWT token in return. 
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
    throw new Error(response.toString());
  }
};

// ---Customer access-requests---

// - Fetches all Jobs belonging to Customer. 
export const fetchAllJobsByCustomerId = async (
  jwtToken: string | null,
  tokenObj: TokenData | null
): Promise<Job[]> => {
  let data: Job[];
  if (jwtToken && tokenObj) {
    const response = await fetch(
      `${API_URL}/job/searchByCustomerId?id=${tokenObj.id}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${jwtToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    data = await response.json();
    return data;
  } else {
    return (data = []);
  }
};

// - Fetches a Review belonging to Customer.
export const fetchReviewByCustomerId = async (
  jwtToken: string | null,
  tokenObj: TokenData | null
): Promise<ReviewDto | null> => {
  let data: ReviewDto | null;
  if (jwtToken && tokenObj) {
    try {
      const response = await fetch(
        `${API_URL}/review/searchCustomerId?id=${tokenObj.id}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${jwtToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      data = await response.json();
      return data;
    } catch {
      return null;
    }
  } else {
    return null;
  }
};

// - Deletes a Review belonging to Customer. 
export const deleteReviewByCustomerId = async (
  jwtToken: string | null,
  tokenObj: TokenData | null
): Promise<number | undefined> => {
  if (jwtToken && tokenObj) {
    const response = await fetch(
      `${API_URL}/review/deleteByCustomerId?id=${tokenObj.id}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${jwtToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    if (response.ok) {
      return response.status;
    } else {
      throw new Error(response.status.toString());
    }
  }
};

// - Create a Job with Customer. 
export const postNewJob = async (
  jwtToken: string | null,
  job: JobSender
): Promise<number> => {
  const response = await fetch(`${API_URL}/job/create`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(job),
  });
  return response.status;
};

// - Create a Review with Customer. 
export const postNewReview = async (
  jwtToken: string | null,
  review: ReviewSender
): Promise<number> => {
  const response = await fetch(`${API_URL}/review/create`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(review),
  });
  return response.status;
};

// - Delete a Customer. 
export const deleteUser = async (
  jwtToken: string | null,
  tokenObj: TokenData | null
) => {
  if (jwtToken && tokenObj) {
    try {
      const response = await fetch(
        `${API_URL}/user/deleteById?id=${tokenObj.id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${jwtToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      return response.status;
    } catch {
      return null;
    }
  } else {
    return null;
  }
};

// - Delete a Job belonging to Customer.
export const deleteJobByCustomerId = async (
  jwtToken: string | null,
  tokenObj: TokenData | null,
  jobId: string
): Promise<number | undefined> => {
  if (jwtToken && tokenObj) {
    const response = await fetch(`${API_URL}/job/delete?id=${jobId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
        "Content-Type": "application/json",
      },
    });
    if (response.ok) {
      return response.status;
    } else {
      throw new Error(response.status.toString());
    }
  }
};

// ---Admin access-requests---

// - Fetches all Jobs.
export const fetchAllJobs = async (
  jwtToken: string | null,
  tokenObj: TokenData | null
): Promise<Job[]> => {
  let jobList: Job[];
  if (jwtToken && tokenObj) {
    const response = await fetch(`${API_URL}/job/getAll`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
        "Content-Type": "application/json",
      },
    });
    jobList = await response.json();
    return jobList;
  }
  return (jobList = []);
};

// - Changes the status of Jobs. (Works both ways). OK, with minor bug (different order on reload).
export const changeJobStatus = async (
  jwtToken: string | null,
  tokenObj: TokenData | null,
  id: string
): Promise<JobDto | null> => {
  let job: JobDto;
  if (jwtToken && tokenObj && id) {
    const response = await fetch(`${API_URL}/job/complete?id=${id}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
        "Content-Type": "application/json",
      },
    });
    job = await response.json();
    return job;
  }
  return null;
};

// - Create a new admin.
export const createNewAdmin = async (
  jwtToken: string | null,
  user: User
): Promise<ResponseEntity> => {
  const response = await fetch(`${API_URL}/auth/signupAdmin`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  });
  if (response.ok) {
    const data = await response.json();
    return { status: response.status, data };
  } else {
    throw new Error(response.status.toString());
  }
};
