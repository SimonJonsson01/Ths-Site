import { useEffect, useState } from "react";
import { ReviewDto } from "../types";
import { fetchReviews } from "../services/api";
import { ReviewCard } from "../components/ReviewCard";
import { LoadingIcon } from "../components/LoadingIcon";

export const Reviewpage = () => {
  const [reviews, setReviews] = useState<ReviewDto[]>([]);
  const [loaded, setLoaded] = useState(false);

  useEffect(() => {
    const getReviews = async (): Promise<void> => {
      const data: ReviewDto[] = await fetchReviews();
      setReviews(data);
      setLoaded(true)
    };
    getReviews();
  }, [setReviews]);

  return (
    <div className="size-full">
      <h3 className="text-center text-5xl font-semibold italic tracking-wide">
        Recensioner
      </h3>
      <div className="site-gutter py-6 flex flex-wrap gap-6 justify-evenly">
        {loaded ? 
        reviews.map((review: ReviewDto) => (
          <ReviewCard review={review} />
        ))
      : <LoadingIcon />}
      </div>
    </div>
  );
};
