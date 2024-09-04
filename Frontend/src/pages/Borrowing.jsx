import { useContent } from "../components/ContentProvider"
import MainContent from "../components/MainContent"
import Sidebar from "../components/Sidebar"

const Borrowing = () => {
  const {field} = useContent()
  return (

    <div className="flex min-h-screen">

    <Sidebar className="flex w-48 " />
    <MainContent type={field[4].borrowing} className="flex-1" />
    </div>
  )
}

export default Borrowing