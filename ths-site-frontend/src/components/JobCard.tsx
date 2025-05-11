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
    <div className="flex items-center w-full">
      <div className="border-1 bg-gray-100 p-3 my-1 flex flex-col gap-2 size-full rounded-xl">
        <strong className="text-xl">{job.title}</strong>
        <span className="px-2 py-4 rounded-sm bg-white break-all inset-shadow-sm inset-shadow-gray-500/50">
          {job.content}
        </span>
        <div className="flex whitespace-break-spaces">
          <strong>Skapad: </strong>
          <span>{displayTime(job.createdAt)}</span>
        </div>
        <div className="flex justify-between *:font-semibold *:shadow">
          {job.completed ? (
            <span className="bg-green-400 text-white p-2 size-fit rounded-md whitespace-nowrap">
              KLAR
            </span>
          ) : (
            <span className="bg-red-400 text-white p-2 size-fit rounded-md whitespace-nowrap">
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
  );
};
