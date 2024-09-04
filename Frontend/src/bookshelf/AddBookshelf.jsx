import { useState } from "react";
import { addBookshelf } from "../utils/fetchAPI.js";
import Sidebar from "../components/Sidebar.jsx";

export default function AddBookshelf() {
  const [newBookshelf, setNewBookshelf] = useState({
    categoryBook: "",
    capacity: 0,
    descriptionBookshelf: "",
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;

    setNewBookshelf({ ...newBookshelf, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await addBookshelf(newBookshelf);
      console.log(res);
      if (res.success) {
        setSuccessMessage("A new bookshelf was added successfully !");
        setNewBookshelf({
          categoryBook: "",
          capacity: 0,
          descriptionBookshelf: "",
        });
        setErrorMessage("");
      }
      if(res === "ERR_BAD_REQUEST"){
        setErrorMessage("Access Denied")
      }
    } catch (error) {
        console.log(error);
        if(error === "ERR_BAD_REQUEST"){
          setErrorMessage("Access Denied")
        }
        else
        setErrorMessage(error)
      }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <>
      <Sidebar />
      <div className="p-8 min-h-screen bg-gradient-to-r ml-44 from-green-400 via-blue-500 to-purple-600 text-white flex justify-center items-center">
        <div className="w-full max-w-lg bg-white p-8 shadow-lg rounded-lg relative">
          {/* Pop-up Success Message */}
          {successMessage && (
            <div className="absolute top-0 left-0 w-full bg-green-500 text-white text-center py-3 rounded-t-lg shadow-lg">
              {successMessage}
            </div>
          )}

          {/* Pop-up Error Message */}
          {errorMessage && (
            <div className="absolute top-0 left-0 w-full bg-red-500 text-white text-center py-3 rounded-t-lg shadow-lg">
              {errorMessage}
            </div>
          )}

          <form
            onSubmit={handleSubmit}
            className="w-full max-w-lg bg-white p-8 shadow-lg rounded-lg"
          >
            <h2 className="text-2xl font-bold mb-6 text-gray-800 text-center">
              Create New Bookshelf
            </h2>

            {/* Category Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="categoryBook"
              >
                Category Book
              </label>
              <input
                type="text"
                id="categoryBook"
                name="categoryBook"
                value={newBookshelf.categoryBook}
                onChange={(e) => {
                  handleChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Category"
              />
            </div>

            {/* Capacity Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="capacity"
              >
                Capacity
              </label>
              <input
                type="number"
                id="capacity"
                name="capacity"
                value={newBookshelf.capacity}
                onChange={(e) => {
                  handleChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Capacity"
              />
            </div>

            {/* Description Bookshelf Field */}
            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="descriptionBookshelf"
              >
                Description Bookshelf
              </label>
              <textarea
                id="descriptionBookshelf"
                name="descriptionBookshelf"
                rows="4"
                value={newBookshelf.descriptionBookshelf}
                onChange={(e) => {
                  handleChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Description Bookshelf"
              ></textarea>
            </div>

            {/* Submit Button */}
            <div className="flex justify-center">
              <button
                type="submit"
                className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                Create
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
