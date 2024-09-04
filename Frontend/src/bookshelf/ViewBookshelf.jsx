import { useEffect } from "react";
import { useState } from "react";
import {
  removeBookshelf,
  viewBookshelf,
  updateBookshelf,
} from "../utils/fetchAPI"; // Import fungsi update
import Sidebar from "../components/Sidebar";
import { ArrowLeft, ArrowRight, Edit, Trash } from "lucide-react";
// import {  useHistory } from "react-router-dom"; // Import useHistory untuk navigasi

const ViewBookshelf = () => {
  const [bookshelf, setBookshelf] = useState([
    {
      id: "",
      categoryBook: "",
      capacity: 0,
      descriptionBookshelf: "",
      fillBookshelf: 0,
    },
  ]);
  const [page, setPage] = useState(1);
  const [orderBy, setOrderBy] = useState("categoryBook");
  const [sortOrder, setSortOrder] = useState("desc");
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [editMode, setEditMode] = useState(false); // Untuk menentukan apakah sedang dalam mode edit
  const [editData, setEditData] = useState({}); // Menyimpan data yang akan diedit
  // const history = useHistory();

  const handleChangeSort = (e) => {
    let value = e.target.value;
    setSortOrder(value);
  };

  const setInformation = (bookshelf) => {
    if (bookshelf.capacity === bookshelf.fillBookshelf) {
      return "This bookshelf is full";
    } else if (bookshelf.fillBookshelf === 0) {
      return "The bookshelf is still empty";
    } else {
      return "Filled with " + bookshelf.fillBookshelf + " books";
    }
  };

  const handlePageRight = () => {
    if (bookshelf.length === 0) {
      setPage(page);
    } else {
      setPage(page + 1);
    }
  };

  const handlePageLeft = () => {
    if (page < 2) {
      setPage(1);
    } else {
      setPage(page - 1);
    }
  };

  const handleChangeOrderBy = (e) => {
    let value = e.target.value;
    setOrderBy(value);
  };

  const fetchBookshelf = async () => {
    setIsLoading(true);
    try {
      const result = await viewBookshelf(page, 5, orderBy, sortOrder);
      setSuccessMessage("Table Information");
      setBookshelf(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      window.scrollTo(0, 0);
      const result = await removeBookshelf(id);

      if (result === "On the bookshelf there are still books available") {
        setErrorMessage(result);
      } else if (result === "Failed remove bookshelf: Bookshelf not found") {
        setErrorMessage(result);
      } else {
        setSuccessMessage(`Book was deleted`);
        setTimeout(() => {
          window.location.reload();
        }, 3000);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  const handleEditClick = (item) => {
    setEditMode(true);
    setEditData(item); // Set data yang akan di-edit
  };

  const handleUpdate = async () => {
    try {
      const result = await updateBookshelf(editData.id, editData); // Memanggil fungsi update
      console.log(result);
      setSuccessMessage("Bookshelf updated successfully");
      setEditMode(false); // Kembali ke mode view setelah update
      fetchBookshelf(); // Refresh data setelah update
      window.location.reload();
    } catch (error) {
      setErrorMessage(error.message);
      console.log(error);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditData({ ...editData, [name]: value }); // Update nilai dari field yang diubah
  };

  useEffect(() => {
    fetchBookshelf();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [orderBy, sortOrder, page]);

  return (
    <>
      <Sidebar />
      <div className="p-8 min-h-screen ml-44 bg-gradient-to-r from-green-400 via-blue-500 to-purple-600 text-white flex justify-center items-center">
        <div className="w-full max-w-3xl bg-white p-8 shadow-lg rounded-lg relative">
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

          <h2 className="text-2xl font-bold text-gray-800 p-6">
            {editMode ? "Edit Bookshelf" : "View Bookshelf"}
          </h2>
          {isLoading ? (
            <div>Loading</div>
          ) : editMode ? (
            <div>
              {/* Form untuk edit */}
              <div className="mb-4">
                <label className="block text-gray-700">Category Book</label>
                <input
                  type="text"
                  name="categoryBook"
                  value={editData.categoryBook}
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Capacity</label>
                <input
                  type="number"
                  name="capacity"
                  value={editData.capacity}
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Description</label>
                <textarea
                  name="descriptionBookshelf"
                  value={editData.descriptionBookshelf}
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                />
              </div>
              <button
                onClick={handleUpdate}
                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              >
                Update
              </button>
            </div>
          ) : (
            <>
              {/* View Table */}
              <div className="flex justify-between items-center px-6 pb-4">
                <div className="flex items-center space-x-4">
                  <div>
                    <label className="mr-2 text-gray-600">Sort By:</label>
                    <select
                      value={orderBy}
                      className="px-4 py-2 border rounded-lg bg-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      onChange={(e) => {
                        handleChangeOrderBy(e);
                      }}
                    >
                      <option value="categoryBook">Category</option>
                      <option value="capacity">Capacity</option>
                    </select>
                  </div>
                  <div>
                    <label className="mr-2 text-gray-600">Order:</label>
                    <select
                      value={sortOrder}
                      className="px-4 py-2 border rounded-lg bg-gray-600  focus:outline-none focus:ring-2 focus:ring-blue-500"
                      onChange={(e) => {
                        handleChangeSort(e);
                      }}
                    >
                      <option value="asc">Ascending</option>
                      <option value="desc">Descending</option>
                    </select>
                  </div>
                  <div className="text-black flex justify-between items-center px-6 pb-4 gap-6">
                    <button
                      onClick={() => {
                        handlePageLeft();
                      }}
                    >
                      <ArrowLeft className="bg-gray-600 stroke-green-400" />
                    </button>
                    Page
                    <button
                      onClick={() => {
                        handlePageRight();
                      }}
                    >
                      <ArrowRight className="bg-gray-600 stroke-green-400" />
                    </button>
                  </div>
                </div>
              </div>

              <div className="overflow-x-auto ">
                <table className="min-w-full bg-white">
                  <thead>
                    <tr className="w-full bg-gray-200 text-gray-600 uppercase text-base leading-normal">
                      <th className="py-3 px-6 text-left">Category Book</th>
                      <th className="py-3 px-6 text-left">Capacity</th>
                      <th className="py-3 px-6 text-left">Description</th>
                      <th className="py-3 px-6 text-left">Information</th>
                      <th className="py-3 px-6 text-left">Actions</th>
                    </tr>
                  </thead>
                  <tbody className="text-gray-600 text-sm font-medium">
                    {bookshelf.map((item, index) => (
                      <tr
                        key={index}
                        className="border-b-2 border-gray-300 hover:bg-slate-300"
                      >
                        <td className="py-3 px-6 text-left whitespace-nowrap">
                          <span className="font-bold">{item.categoryBook}</span>
                        </td>
                        <td className="py-3 px-6 text-left">{item.capacity}</td>
                        <td className="py-3 px-6 text-left">
                          {item.descriptionBookshelf}
                        </td>
                        <td className="py-3 px-6 text-left">
                          {setInformation(item)}
                        </td>
                        <td className="py-3 px-1 text-left flex gap-2">
                          <button
                            className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-2 rounded"
                            onClick={() => handleEditClick(item)}
                          >
                            <Edit />
                          </button>
                          <button
                            className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-2 rounded"
                            onClick={() => handleDelete(item.id)}
                          >
                            <Trash />
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default ViewBookshelf;
