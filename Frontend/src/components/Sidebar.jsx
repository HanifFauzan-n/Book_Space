import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <aside className="fixed top-0 left-0 w-48 h-screen bg-gray-800 text-white shadow-md flex flex-col">
      <div className="p-6 text-2xl font-extrabold">
        Book Space
      </div>
      <nav className="flex-1 overflow-y-auto">
        <ul>
          <li className="hover:bg-gray-700">
            <a href="#home" className="block p-4 transition-colors duration-300">Home</a>
          </li>
          <li className="hover:bg-gray-700">
            <a href="#catalog" className="block p-4 transition-colors duration-300">Catalog</a>
          </li>
          <li className="hover:bg-gray-700">
            <a href="#contact" className="block p-4 transition-colors duration-300">Contact</a>
          </li>
        </ul>
      </nav>
      <div className="bg-gray-700 p-4">
        <Link to="/login" className="block w-full text-center py-2 bg-blue-500 rounded hover:bg-blue-600 transition-colors duration-300">Login</Link>
      </div>
    </aside>
  );
};

export default Sidebar;
