interface ButtonProps {
  text: string;
}

export const Button = ({ text }: ButtonProps) => {
  return (
    <div className="rounded-xl p-4 bg-gray-50 size-fit font-semibold cursor-pointer hover:bg-gray-100 active:bg-gray-200 hover:underline">
      {text}
    </div>
  );
};
