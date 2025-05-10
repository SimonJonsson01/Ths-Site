import { useState } from "react";
import { displayTime } from "../services/services";
import { Job } from "../types";
import { changeJobStatus } from "../services/api";
import { useToken } from "../stores/TokenStore";

interface JobCardProps {
  job: Job;
}

export const JobCardAdmin = ({ job }: JobCardProps) => {
  const { jwtToken, tokenObj } = useToken();
  const [jobStatus, setJobStatus] = useState<boolean>(job.completed);

  // - Calls the request to change the status on said job, and if ok changes the "jobStatus" value.
  const handleJobStatus = async () => {
    try {
      const response = await changeJobStatus(jwtToken, tokenObj, job.id);
      if (response) {
        if (response.completed != jobStatus) {
          setJobStatus(!jobStatus)
        }
      }
    } catch {
      throw new Error();
    }
  };

  return (
    <div className="border-2 rounded-md flex flex-col bg-amber-600 p-2">
      <span className="text-3xl my-2">{job.title}</span>
      <span>{job.user?.firstName + " " + job.user?.lastName}</span>
      <span className="p-1 py-2 bg-white rounded-md break-all">{job.content}</span>
      <span>{displayTime(job.createdAt)}</span>
      <div className="py-2">
        {jobStatus ? (
          <button
            onClick={handleJobStatus}
            className="bg-green-300 hover:bg-green-500 hover:cursor-pointer p-2 size-fit rounded-md"
          >
            KLAR
          </button>
        ) : (
          <button
            onClick={handleJobStatus}
            className="bg-red-300 hover:bg-red-500 hover:cursor-pointer  p-2 size-fit rounded-md"
          >
            INTE KLAR
          </button>
        )}
      </div>
    </div>
  );
};
