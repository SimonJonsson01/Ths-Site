// - Used to convert and display time (in the JobCard-components).
export const displayTime = (createdAt: Date): string => {
  const date = new Date(createdAt);
  return date.toString().split(" GMT")[0];;
};
