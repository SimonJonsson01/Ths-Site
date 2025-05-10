import { deleteJobByCustomerId } from "../services/api";
import { displayTime } from "../services/services";
import { useToken } from "../stores/TokenStore";
import { Job } from "../types";

interface JobCardProps {
  job: Job;
  onDelete: () => void;
}

// onDelete is a callback functions that updates the JobList in Customerpage.tsx.
export const JobCard = ({ job, onDelete }: JobCardProps) => {
  const { jwtToken, tokenObj } = useToken();

  // Tries to delete the Job by it's id, if ok uses the onDelete (see commment above).
  const handleDeleteJob = async () => {
    try {
      const response = await deleteJobByCustomerId(jwtToken, tokenObj, job.id);
      if (response) {
        onDelete();
      }
    } catch {
      throw new Error();
    }
  };

  return (
    <>
      <div className="flex items-center gap-2">
        <div className="border-2 bg-pink-300 p-2 my-1 flex flex-col size-full">
          <strong>{job.title}</strong>
          <span className="px-2 py-4 rounded-sm bg-white break-all">{job.content}</span>
          <div className="flex whitespace-break-spaces">
            <strong>Skapad: </strong>
            <span>{displayTime(job.createdAt)}</span>
          </div>
          <div className="flex justify-between">
            {job.completed ? (
              <span className="bg-green-300 p-2 size-fit rounded-md whitespace-nowrap">
                KLAR
              </span>
            ) : (
              <span className="bg-red-200 p-2 size-fit rounded-md whitespace-nowrap">
                INTE KLAR
              </span>
            )}
            <button
              onClick={handleDeleteJob}
              className="bg-red-300 hover:bg-red-400 hover:cursor-pointer size-fit rounded-xl p-3"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </>
  );
};
