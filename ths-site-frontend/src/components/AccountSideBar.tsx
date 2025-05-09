import { TokenData } from "../types";
interface AccountSideBarProps {
  tokenObj: TokenData | null;
}
export const AccountSideBar = ({ tokenObj }: AccountSideBarProps) => {
  function translateUsertype() {
    switch (tokenObj?.userType) {
      case "customer":
        return "Kund";
      case "admin":
        return "Administratör";
    }
  }

  return (
    <aside className="flex flex-col border-2 w-full max-w-80 p-2 gap-2">
      <span className="text-5xl border-b-2 pb-2">{tokenObj?.name}</span>
      <span className="text-xl">Email: {tokenObj?.email}</span>
      <span>{translateUsertype()}</span>
    </aside>
  );
};
