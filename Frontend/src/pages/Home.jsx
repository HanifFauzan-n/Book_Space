import Welcome from "../components/Welcome"
import Sidebar from "../components/Sidebar"

const Home = () => {
  return (

    <div className="flex min-h-screen">

    <Sidebar className="flex w-48 " />
    <Welcome className="flex-1" />
    </div>
  )
}

export default Home