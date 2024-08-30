/* eslint-disable react/prop-types */

import { Link } from "react-router-dom";

const MainContent = ({type}) => {
  return (
    <div className="flex-1 p-6 bg-gradient-to-r ml-44 from-green-400 via-blue-500 to-purple-600 text-white">
      <h1 className="text-3xl font-bold mb-8">CRUD Operations</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Link to={type.link[0]} className="bg-white text-gray-800 p-6 rounded-lg shadow-lg hover:shadow-2xl transform hover:scale-105 transition duration-300">
        <div >
          <h2 className="text-xl font-semibold mb-4">Create</h2>
          <p className="text-sm">Add new {type.name} to the database.</p>
        </div>
        </Link>

        <Link to={type.link[1]} className="bg-white text-gray-800 p-6 rounded-lg shadow-lg hover:shadow-2xl transform hover:scale-105 transition duration-300">
          <h2 className="text-xl font-semibold mb-4">Read</h2>
          <p className="text-sm">View existing {type.name} from the database.</p>
        </Link >

        <Link to={type.link[2]} className="bg-white text-gray-800 p-6 rounded-lg shadow-lg hover:shadow-2xl transform hover:scale-105 transition duration-300">
          <h2 className="text-xl font-semibold mb-4">Update</h2>
          <p className="text-sm">Modify {type.name} in the database.</p>
        </Link>

        <Link to={type.link[3]} className="bg-white text-gray-800 p-6 rounded-lg shadow-lg hover:shadow-2xl transform hover:scale-105 transition duration-300">
          <h2 className="text-xl font-semibold mb-4">Delete</h2>
          <p className="text-sm">Remove {type.name} from the database.</p>
        </Link>
      </div>
    </div>
  );
};

export default MainContent;
