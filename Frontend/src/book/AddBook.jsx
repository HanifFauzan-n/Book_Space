import { useState } from "react";
import { addBook } from "../utils/fetchAPI.js";
import Sidebar from "../components/Sidebar.jsx";

const AddBook = () => {
  const [newBook, setNewBook] = useState({
    author: "",
    descriptionBook: "",
    categoryBook: "",
    fill: 0,
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleRoomInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;

    setNewBook({ ...newBook, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await addBook(newBook);
      console.log(res);
      if (res.success) {
        setSuccessMessage("A new Book was added successfully !");
        setNewBook({
          author: "",
          descriptionBook: "",
          categoryBook: "",
          fill: 0,
        });
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new bookshelf");
      }
    } catch (error) {
      console.log(error);
      setErrorMessage(error);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <>
      <Sidebar />
      <div className=" p-8 min-h-screen bg-gradient-to-r ml-44 from-green-400 via-blue-500 to-purple-600 text-white flex justify-center items-center">
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
              Create New Book
            </h2>

            {/* Book Title Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="bookTitle"
              >
                Book Title
              </label>
              <input
                type="text"
                id="bookTitle"
                name="bookTitle"
                onChange={(e) => {
                  handleRoomInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Book Title"
              />
            </div>

            {/* Author Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="author"
              >
                Author
              </label>
              <input
                type="text"
                id="author"
                name="author"
                onChange={(e) => {
                  handleRoomInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Author Name"
              />
            </div>

            {/* Description Book Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="descriptionBook"
              >
                Description
              </label>
              <textarea
                id="descriptionBook"
                name="descriptionBook"
                rows="4"
                onChange={(e) => {
                  handleRoomInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Description"
              ></textarea>
            </div>

            {/* Category Book Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="categoryBook"
              >
                Category
              </label>
              <input
                type="text"
                id="categoryBook"
                name="categoryBook"
                onChange={(e) => {
                  handleRoomInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Category"
              />
            </div>

            {/* Fill Field */}
            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="fill"
              >
                Fill
              </label>
              <input
                type="number"
                id="fill"
                name="fill"
                onChange={(e) => {
                  handleRoomInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Fill Information"
              />
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
};

export default AddBook;
