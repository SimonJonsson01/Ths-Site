import { ReactNode } from "react";
import { ReviewDto } from "../types";

interface Props {
  review: ReviewDto;
}

export const ReviewCard = ({ review }: Props) => {
  function getScoreAsStars(stars: number): ReactNode {
    return (
      <div className="flex flex-row gap-2">
        {Array.from({ length: stars }, () => (
          <div className="size-12">
            <img src="../one-star-icon.svg" alt="*" className="size-12" />
          </div>
        ))}
      </div>
    );
  }
  /* max-w-60 */
  return (
    <div className="bg-blue-50 flex flex-col p-4 shadow rounded-2xl font-semibold w-full max-w-116 max-h-92 border-2">
      <div className="h-12 w-fit">{getScoreAsStars(review.score)}</div>
      <h3 className="text-3xl py-2">{review.title ? review.title : "null"}</h3>
      <span className="font-semibold size-fit my-auto">{review.content}</span>
      <span className="italic font-normal">
        {" - " +
          review.user.firstName +
          " " +
          review.user.lastName.charAt(0) +
          "."}
      </span>
    </div>
  );
};
