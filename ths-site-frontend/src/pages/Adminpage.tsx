import { useState } from "react";
import { AccountSideBar } from "../components/AccountSideBar";
import { createNewAdmin } from "../services/api";
import { useToken } from "../stores/TokenStore";
import { User, ResponseEntity } from "../types";

export const Adminpage = () => {
  const { jwtToken, tokenObj } = useToken();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function clearInput() {
    setFirstName("");
    setLastName("");
    setEmail("");
    setPassword("");
  }

  const createNewUser = (
    firstName: string,
    lastName: string,
    email: string,
    password: string
  ) => {
    const User: User = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
    };
    return User;
  };

  // Tries to post and save a new Admin, if ok resets the input fields.
  const handleNewAdmin = async (user: User) => {
    try {
      const response: ResponseEntity = await createNewAdmin(jwtToken, user);
      if (response.status === 200) {
        clearInput();
      }
    } catch {
      throw new Error();
    }
  };

  return (
    <div className="bg-green-300 size-full flex">
      <AccountSideBar tokenObj={tokenObj} />
      <span></span>
      <div className="bg-green-900 border-2 mx-20 size-full">
        <form
          className="border-2 bg-pink-300 mx-auto p-10 size-fit"
          onSubmit={(ev) => {
            ev.preventDefault();
            handleNewAdmin(createNewUser(firstName, lastName, email, password));
          }}
        >
          <h3 className="text-3xl text-center">Skapa ny administratör</h3>

          <div className="flex flex-col items-center mx-auto *:my-1">
            <strong className="w-full">Förnamn</strong>
            <input
              placeholder="Förnamn..."
              value={firstName}
              onChange={(ev) => setFirstName(ev.target.value)}
              className="bg-blue-50 rounded p-1 w-96"
            ></input>
            <strong className="w-full">Efternamn</strong>
            <input
              placeholder="Efternamn..."
              value={lastName}
              onChange={(ev) => setLastName(ev.target.value)}
              className="bg-blue-50 rounded p-1 w-96"
            ></input>
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
            type="submit"
            className="rounded-xl mt-6 p-3 bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-200 active:bg-gray-300"
          >
            Skapa ny admin
          </button>
        </form>
      </div>
    </div>
  );
};
