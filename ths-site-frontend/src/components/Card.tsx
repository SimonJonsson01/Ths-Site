interface CardProps {
  children: React.ReactNode
}

export const Card = ({children}: CardProps) => {
  return (
    <div className="rounded-xl p-4 bg-gray-50">
      {children}
    </div>
  )
}