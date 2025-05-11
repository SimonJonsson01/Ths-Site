import { useState } from "react";

type StarRatingCompProps = {
  maxStars?: number;
  onRate: (rating: number) => void;
};

/* 
The component is used in the ReviewWritepage.tsx.
OnRate is a callback to return the "rating" value to ReviewWritepage.tsx.
*/
export const StarRatingComp = ({
  maxStars = 5,
  onRate,
}: StarRatingCompProps) => {
  const [rating, setRating] = useState(0);
  const [hovered, setHovered] = useState(0);

  const handleClick = (value: number) => {
    setRating(value);
    onRate(value);
  };

  return (
    <div className="flex gap-2 *:cursor-pointer size-full">
      {Array.from({ length: maxStars }, (_, i) => {
        const starValue = i + 1;
        return (
          <span
            key={starValue}
            onClick={() => handleClick(starValue)}
            onMouseEnter={() => setHovered(starValue)}
            onMouseLeave={() => setHovered(0)}
            className={`${
              starValue <= (hovered || rating)
                ? "text-yellow-400"
                : "text-gray-600"
            } text-6xl text-shadow-sm text-shadow-gray-400`}
          >
            ★
          </span>
        );
      })}
    </div>
  );
};
