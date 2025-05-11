import { useState } from "react";
import { Link } from "react-router-dom";
import { ResponseEntity, User } from "../types";
import { createNewCustomer } from "../services/api";
import { useNavigate } from "react-router-dom";

export const SignupPage = () => {
  const navigate = useNavigate();
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

  const handleNewCustomer = async (user: User) => {
    try {
      const response: ResponseEntity = await createNewCustomer(user);
      if (response.status === 200) {
        clearInput();
        navigate("/login");
      }
    } catch {
      throw new Error();
    }
  };

  return (
    <div className="pt-8">
      <form
        className="border-1 bg-gray-300 mx-auto p-10 size-fit rounded-md shadow"
        onSubmit={(ev) => {
          ev.preventDefault();
          handleNewCustomer(
            createNewUser(firstName, lastName, email, password)
          );
        }}
      >
        <h3 className="text-3xl text-center">Skapa konto</h3>

        <div className="flex flex-col items-center mx-auto *:my-1">
          <strong className="w-full">Förnamn</strong>
          <input
            placeholder="Förnamn..."
            value={firstName}
            onChange={(ev) => setFirstName(ev.target.value)}
            className="bg-white rounded p-1 w-96"
          ></input>
          <strong className="w-full">Efternamn</strong>
          <input
            placeholder="Efternamn..."
            value={lastName}
            onChange={(ev) => setLastName(ev.target.value)}
            className="bg-white rounded p-1 w-96"
          ></input>
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
          <button className="rounded-xl my-6 p-3 mx-auto bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-200 active:bg-gray-300">
            Skapa konto
          </button>
        </div>
        <div className="flex gap-2 w-full justify-center">
          <span>Har du redan ett konto?</span>
          <Link className="text-blue-600" to={"/login"}>
            Logga in
          </Link>
        </div>
      </form>
    </div>
  );
};
