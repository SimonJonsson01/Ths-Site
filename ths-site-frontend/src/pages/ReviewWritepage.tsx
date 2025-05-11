import { useNavigate } from "react-router-dom";
import { useToken } from "../stores/TokenStore";
import { useState } from "react";
import { ReviewSender } from "../types";
import { postNewReview } from "../services/api";
import { StarRatingComp } from "../components/StarRatingComp";

export const ReviewWritepage = () => {
  const navigate = useNavigate();
  const { jwtToken, tokenObj } = useToken();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [score, setScore] = useState<number>(0);
  const MAX_LENGTH = 255;

  // This function is to access and store the value from the StarRatingComp.tsx.
  const handleScore = (value: number) => {
    setScore(value);
  };

  // This function handles the logic before sending the request, if ok the user returns to their own page.
  const handleSubmit = async () => {
    if (tokenObj == undefined) {
      return;
    }
    if (score == 0) {
      return;
    }
    const review: ReviewSender = {
      title: title,
      content: content,
      score: score,
      customerId: tokenObj.id,
    };
    try {
      const response = await postNewReview(jwtToken, review);
      if (response === 200) {
        navigate("/customer");
      }
    } catch {
      throw new Error("ERROR!");
    }
  };

  // This function handles the content-input to make sure the backend can handle the message.
  const handleContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length <= MAX_LENGTH) {
      setContent(value);
    }
  };

  return (
    <div className="size-full pt-8">
      <form
        className="border-1 bg-gray-300 mx-auto p-10 size-fit flex flex-col shadow rounded-md"
        onSubmit={(ev) => {
          ev.preventDefault();
        }}
      >
        <h3 className="text-3xl text-center">Skriv en recension</h3>
        <div className="flex flex-col items-center mx-auto *:my-1">
          <strong className="w-full">Titel</strong>
          <input
            placeholder="Titel"
            value={title}
            onChange={(ev) => setTitle(ev.target.value)}
            className="bg-blue-50 rounded p-1 w-120"
          ></input>
          <div className="w-full flex">
            <strong className="w-full">Betyg</strong>
            <div className="flex gap-2">
              {<StarRatingComp onRate={handleScore} />}
            </div>
          </div>
          <strong className="w-full">Beskrivning</strong>
          <textarea
            placeholder="Beskriv kvaliten av servicen..."
            value={content}
            onChange={handleContent}
            className="bg-blue-50 rounded p-1 w-120 h-48"
          ></textarea>
        </div>
        <div className="size-fit">
          {content.length} / {MAX_LENGTH} tecken
        </div>

        <button
          onClick={handleSubmit}
          className="rounded-xl mx-auto mt-6 p-3 bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-200 active:bg-gray-300"
        >
          Spara recension
        </button>
      </form>
    </div>
  );
};
