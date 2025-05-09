import { AccountSideBar } from "../components/AccountSideBar";
import { useToken } from "../stores/TokenStore";

export const Customerpage = () => {
  const { jwtToken, tokenObj } = useToken();



  return (
    <div className="bg-green-300 size-full flex">
      <AccountSideBar tokenObj={tokenObj} />
      <span></span>
      <div className="bg-green-900 border-2 mx-20 size-full">
        <div className="bg-amber-500 border-2">
          <span>Review</span>
        </div>
        <div className="bg-red-500 border-2">
          <span>Alla jobb</span>
        </div>
      </div>
    </div>
  );
};
