import { create } from "zustand";
import { TokenData } from "../types";
import { jwtDecode } from "jwt-decode";

interface TokenStore {
  jwtToken: string | null;
  tokenObj: TokenData | null;
  setJwtToken: (token: string) => void;
  deleteJwtToken: () => void;
  setTokenObj: (token: string) => void;
}

export const useToken = create<TokenStore>()((set) => ({
  jwtToken: sessionStorage.getItem("token") || null,
  tokenObj: null,
  setJwtToken: (token) => {
    sessionStorage.setItem("token", token);
    set({ tokenObj: jwtDecode(token) });
    set({ jwtToken: token });
  },
  deleteJwtToken: () => {
    sessionStorage.removeItem("token");
    set({ tokenObj: null });
    set({ jwtToken: null });
  },
  setTokenObj: (token) => {
    set({ tokenObj: jwtDecode(token) });
  },
}));
