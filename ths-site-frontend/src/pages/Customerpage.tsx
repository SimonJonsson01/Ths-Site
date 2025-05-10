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
    <div className="bg-green-300 size-full flex">
      <AccountSideBar tokenObj={tokenObj} />
      <span></span>
      <div className="bg-green-900 border-2 mx-20 size-full">
        <div className="bg-amber-500 border-2 border-green-400 flex flex-col items-center">
          <div className="flex gap-2 my-2">
            <span className="text-3xl bg-amber-300">Din recension</span>
            {review ? (
              <button
                onClick={handleDeleteReview}
                className="bg-red-400 p-2 rounded-md hover:bg-red-500 hover:cursor-pointer"
              >
                Radera recension
              </button>
            ) : (
              <></>
            )}
          </div>
          {review != null ? <ReviewCard review={review} /> : <span></span>}
        </div>
        <div className="bg-red-500 border-2 border-blue-400 flex flex-col items-center px-4">
          <span className="text-3xl bg-amber-300">Dina serivceansökan</span>
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
