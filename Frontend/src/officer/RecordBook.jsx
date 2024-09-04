import { useState } from "react";
import Sidebar from "../components/Sidebar.jsx";
import { recordBook } from "../utils/fetchAPI.js";

const RecordBook = () => {
  const [borrowingBook, setBorrowingBook] = useState({
    borrowerName: "",
    borrowedBook: "",

  });
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");


  const handleInputChange = (e) => {
    setBorrowingBook({ ...borrowingBook, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await recordBook(borrowingBook);
      console.log(res.json);
      if (res.success) {
        setSuccessMessage("Book borrowing successfully recorded!");
        setBorrowingBook({
            borrowerName: "",
            borrowedBook: "",
        
        });
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new member");
      }
    } catch (error) {
      console.log(error);
      if(error === "ERR_BAD_REQUEST"){
        setErrorMessage("Access Denied")
      }
      else
      setErrorMessage(error)
    }
    window.scrollTo(0,0);
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
          <h2 className="text-2xl font-bold mb-6 mt-5 text-gray-800 text-center">
            Recording Book
          </h2>

          <form onSubmit={handleSubmit}>
            {/* Name Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="borrowerName"
              >
                Borrower Name
              </label>
              <input
                type="text"
                id="borrowerName"
                name="borrowerName"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter borrower Name"
              />
            </div>

            {/* Age Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="borrowedBook"
              >
                Borrowed Book
              </label>
              <input
                type="text"
                id="borrowedBook"
                name="borrowedBook"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter borrowed Book"
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

export default RecordBook;
