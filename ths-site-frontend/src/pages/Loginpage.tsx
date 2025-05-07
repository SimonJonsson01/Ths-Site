import { useState } from "react";
import { LoginTokenResponse, UserLogin } from "../types";
import { loginUser } from "../services/api";
import { useNavigate } from "react-router-dom";

export const Loginpage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("thony@ths.nu");
  const [password, setPassword] = useState("abc123");

  //const { jwtToken, setJwtToken } = useToken();


  const handleSubmit = async (email: string, password: string) => {
    const user: UserLogin = {
      email: email,
      password: password,
    };
    try {
      const response: LoginTokenResponse = await loginUser(user);

      if (response.status === 200) {
        console.log("TOKEN: ", response.token);

        //const token: string = response.token;
        //sessionStorage.setItem("token", token);
        navigate("/");
      }
    } catch {
      console.log("ERROR!");
    }
  };

  return (
    <form
      className="border-2 bg-pink-300 mx-auto p-10 size-fit"
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
          className="bg-blue-50 rounded p-1 w-96"
        ></input>
        <strong className="w-full">Lösenord</strong>
        <input
          type="password"
          placeholder="Lösenord"
          value={password}
          onChange={(ev) => setPassword(ev.target.value)}
          className="bg-blue-50 rounded p-1 w-96"
        ></input>
      </div>
      <button
        className="rounded-xl mt-6 p-3 bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-200 active:bg-gray-300"
        type="submit"
      >
        Logga in
      </button>
    </form>
  );
};
