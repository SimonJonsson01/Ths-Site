import { useEffect, useState } from "react";
import { AccountSideBar } from "../components/AccountSideBar";
import { useToken } from "../stores/TokenStore";
import { Job, ReviewDto } from "../types";
import {
  deleteReviewByCustomerId,
  fetchAllJobsByCustomerId,
  fetchReviewByCustomerId,
} from "../services/api";
import { ReviewCard } from "../components/ReviewCard";
import { JobCard } from "../components/JobCard";

export const Customerpage = () => {
  const { jwtToken, tokenObj } = useToken();

  const [review, setReview] = useState<ReviewDto | null>(null);
  const [jobs, setJobs] = useState<Job[]>([]);
  const [loaded, setLoaded] = useState(false);

  useEffect(() => {
    getInfo();
  }, [setJobs]);

  // Fetches all Jobs and Review related to Customer (from jwtToken, stored in Session Storage).
  const getInfo = async (): Promise<void> => {
    const jobList: Job[] = await fetchAllJobsByCustomerId(
      jwtToken,
      tokenObj
    );
    const reviewDto: ReviewDto | null = await fetchReviewByCustomerId(
      jwtToken,
      tokenObj
    );
    setJobs(jobList);
    setReview(reviewDto);
    setLoaded(true);
  };

  // Trys deleting the review in backend, and if ok, removes the review in useState.
  const handleDeleteReview = async () => {
    const answer = confirm("Är du säker på att du vill radera din recension?");
    if (answer) {
      const response = await deleteReviewByCustomerId(jwtToken, tokenObj);
      if (response === 200) {
        setReview(null);
      }
    }
  };

  return (
    <div className="bg-gray-600 size-full flex p-3">
      <AccountSideBar tokenObj={tokenObj} />
      <span></span>
      <div className="mx-auto md:mx-24 size-full">
        <div className="flex flex-col items-center my-2">
          <div className="flex gap-6 my-2">
            <span className="text-white text-3xl font-semibold">Din recension</span>
            {review ? (
              <button
                onClick={handleDeleteReview}
                className="bg-red-400 p-2 rounded-md hover:bg-red-500 hover:cursor-pointer font-semibold shadow"
              >
                Radera recension
              </button>
            ) : (
              <></>
            )}
          </div>
          {review != null ? <ReviewCard review={review} /> : <span></span>}
        </div>
        <div className="flex flex-col items-center p-4">
          <span className="text-white text-3xl font-semibold">Dina serivceansökan</span>
          {loaded ? (
            jobs.map((job: Job) => <JobCard job={job} onDelete={getInfo} />)
          ) : (
            <></>
          )}
        </div>
      </div>
    </div>
  );
};
