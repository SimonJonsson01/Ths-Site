import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useToken } from "../stores/TokenStore";
import { postNewJob } from "../services/api";
import { JobSender } from "../types";

export const Formpage = () => {
  const navigate = useNavigate();
  const { jwtToken, tokenObj } = useToken();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const MAX_LENGTH = 255;

  // This function handles the logic before sending the request, if ok the user returns to their own page.
  const handleSubmit = async () => {
    if (tokenObj == undefined) {
      return;
    }
    const job: JobSender = {
      title: title,
      content: content,
      customerId: tokenObj.id,
    };
    try {
      const response = await postNewJob(jwtToken, job);
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
        <h3 className="text-3xl text-center">Ansök efter service</h3>

        <div className="flex flex-col items-center mx-auto *:my-1">
          <strong className="w-full">Titel</strong>
          <input
            placeholder="Titel"
            value={title}
            onChange={(ev) => setTitle(ev.target.value)}
            className="bg-blue-50 rounded p-1 w-120"
          ></input>
          <strong className="w-full">Beskrivning</strong>
          <textarea
            placeholder="Beskriv vad som är fel..."
            value={content}
            onChange={handleContent}
            className="bg-blue-50 rounded p-1 w-120 h-80"
          ></textarea>
        </div>
        <div className="size-fit">
          {content.length} / {MAX_LENGTH} tecken
        </div>

        <button
          onClick={handleSubmit}
          className="rounded-xl mx-auto mt-6 p-3 bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-200 active:bg-gray-300"
        >
          Skicka serviceansökan
        </button>
      </form>
    </div>
  );
};
