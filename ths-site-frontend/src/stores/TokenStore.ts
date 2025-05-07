import { create } from "zustand";

interface TokenStore {
  jwtToken: string | null;
  setJwtToken: (data: string) => void;
  deleteJwtToken: () => void;
}

export const useToken = create<TokenStore>()((set) => ({
  jwtToken: sessionStorage.getItem("token"),
  setJwtToken: (data) => set({ jwtToken: data }),
  deleteJwtToken: () => set({ jwtToken: null }),
}));
