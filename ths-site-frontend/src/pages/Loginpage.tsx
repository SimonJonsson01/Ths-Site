import { useState } from "react";
import { LoginTokenResponse, UserLogin } from "../types";
import { loginUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import { useToken } from "../stores/TokenStore";

export const Loginpage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const { jwtToken, setJwtToken } = useToken();

  const handleSubmit = async (email: string, password: string) => {
    const user: UserLogin = {
      email: email,
      password: password,
    };
    try {
      const response: LoginTokenResponse = await loginUser(user);

      if (response.status === 200) {
        const token: string = response.token;
        setJwtToken(token);
        navigate("/");
      }
    } catch {
      throw new Error();
    }
  };
  if (jwtToken != null) {
    navigate("/");
  }
  return (
    <div className="pt-8">
      <form
        className="border-1 bg-gray-300 mx-auto p-10 size-fit rounded-md shadow"
        onSubmit={(ev) => {
          ev.preventDefault();
          handleSubmit(email, password);
        }}
      >
        <h3 className="text-3xl text-center">Logga in</h3>
        <div className="flex flex-col items-center mx-auto *:my-1">
          <strong className="w-full">Epost-adress</strong>
          <input
            placeholder="Epost-adress..."
            value={email}
            onChange={(ev) => setEmail(ev.target.value)}
            className="bg-white rounded p-1 w-96"
          ></input>
          <strong className="w-full">Lösenord</strong>
          <input
            type="password"
            placeholder="Lösenord"
            value={password}
            onChange={(ev) => setPassword(ev.target.value)}
            className="bg-white rounded p-1 w-96"
          ></input>
        </div>
        <div className="size-full flex">
          <button
            className="rounded-xl my-6 p-3 mx-auto bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-200 active:bg-gray-300"
            type="submit"
          >
            Logga in
          </button>
        </div>
      </form>
    </div>
  );
};
