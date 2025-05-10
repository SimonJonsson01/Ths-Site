import { useNavigate } from "react-router-dom";
import { deleteUser } from "../services/api";
import { useToken } from "../stores/TokenStore";
import { TokenData } from "../types";
interface AccountSideBarProps {
  tokenObj: TokenData | null;
}
export const AccountSideBar = ({ tokenObj }: AccountSideBarProps) => {
  const navigate = useNavigate();
  const { jwtToken, deleteJwtToken } = useToken();

  // This function translate the type of user to
  function translateUsertype() {
    switch (tokenObj?.userType) {
      case "customer":
        return "Kund";
      case "admin":
        return "Administratör";
    }
  }

  // - Deletes user, if ok: it removes jwt token in SessionStoreage and navigate to the front page.
  const handleDeleteUser = async () => {
    const answer = confirm(
      "Är du säker på att du vill radera ditt konto? DETTA GÅR INTE ATT ÅNGRA!"
    );
    if (answer) {
      const response = await deleteUser(jwtToken, tokenObj);
      if (response === 200) {
        deleteJwtToken();
        navigate("/");
      }
    }
  };

  return (
    <aside className="flex flex-col border-2 w-full max-w-80 p-2">
      <div className="border-b-2 flex flex-col">
        <span className="text-5xl font-semibold">{tokenObj?.name}</span>
        <span className="text-xl py-2">{translateUsertype()}</span>
      </div>
      <button
        onClick={handleDeleteUser}
        className="rounded-xl mx-auto mt-12 p-3 bg-red-400 size-fit font-semibold cursor-pointer hover:bg-red-500 active:bg-red-600"
      >
        Radera {translateUsertype()?.toLowerCase()}
      </button>
    </aside>
  );
};
