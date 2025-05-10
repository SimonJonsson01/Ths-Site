import { useEffect, useState } from "react";
import { fetchAllJobs } from "../services/api";
import { useToken } from "../stores/TokenStore";
import { Job } from "../types";
import { JobCardAdmin } from "../components/JobCardAdmin";

export const Jobpage = () => {
  const { jwtToken, tokenObj } = useToken();
  const [jobs, setJobs] = useState<Job[]>([]);
  const [loaded, setLoaded] = useState(false);

  // useEffect calls for a List of all Jobs.
  useEffect(() => {
    const getJobs = async () => {
      const data: Job[] = await fetchAllJobs(jwtToken, tokenObj);
      setJobs(data);
      setLoaded(true);
    };
    getJobs();
  }, [setJobs]);

  return (
    <div>
      {loaded ? (
        <div className="bg-blue-800 size-fit mx-auto">
          {jobs.map((job: Job) => (
            <JobCardAdmin job={job} />
          ))}
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
};
