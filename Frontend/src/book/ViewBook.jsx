import { useEffect, useState } from "react";
import { viewBook, removeBook, updateBook } from "../utils/fetchAPI"; // Pastikan updateBook ada
import Sidebar from "../components/Sidebar";
import { ArrowLeft, ArrowRight, Edit, Trash } from "lucide-react";

const ViewBook = () => {
  const [book, setBook] = useState([
    {
      id: "",
      bookTitle: "",
      author: "",
      stockBook: 0,
      description: "",
      statusBook: "",
      recordingDate: "",
      bookshelf: {},
    },
  ]);
  const [page, setPage] = useState(1);
  const [orderBy, setOrderBy] = useState("bookTitle");
  const [sortOrder, setSortOrder] = useState("desc");
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [editMode, setEditMode] = useState(false); // Menentukan mode edit
  const [editData, setEditData] = useState({}); // Menyimpan data yang akan diedit

  const handleChangeSort = (e) => {
    let value = e.target.value;
    setSortOrder(value);
  };

  const handlePageRight = () => {
    if (book.length === 0) {
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

  const fetchBook = async () => {
    console.log(orderBy);
    console.log(sortOrder);
    console.log(page);
    console.log(book);
    setIsLoading(true);
    try {
      const result = await viewBook(page, 5, orderBy, sortOrder);
      console.log(result);
      setSuccessMessage("Table Information");
      setBook(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      window.scrollTo(0, 0);
      const result = await removeBook(id);
      console.log(result);
      if (result) {
        setSuccessMessage(`Book was deleted`);
        fetchBook(); // Refresh data setelah delete
      } else {
        console.error(`Failed deleting book : ${result.responseMessage}`);
        setErrorMessage(`Failed deleting book: ${result.responseMessage}`);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  // Fungsi untuk menangani klik edit
  const handleEditClick = (item) => {
    setEditMode(true);
    setEditData(item); // Mengisi data yang akan diedit
  };

  // Fungsi untuk menangani perubahan input pada form edit
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditData({ ...editData, [name]: value });
  };

  // Fungsi untuk menangani pembaruan data
  const handleUpdate = async () => {
    try {
      const result = await updateBook(editData.id, editData); // Pastikan updateBook ada di fetchAPI
      console.log(result)
      setSuccessMessage("Book updated successfully");
      setEditMode(false); // Keluar dari mode edit
      fetchBook(); // Refresh data setelah update
    } catch (error) {
      setErrorMessage(error);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  useEffect(() => {
    fetchBook();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [orderBy, sortOrder, page]);

  return (
    <>
      <Sidebar />
      <div className="p-8 min-h-screen ml-44 bg-gradient-to-r from-green-400 via-blue-500 to-purple-600 text-white flex justify-center items-center">
        <div className="w-full max-w-5xl bg-white p-8 shadow-lg rounded-lg relative">
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
            {editMode ? "Edit Book" : "View Book"}
          </h2>
          {isLoading ? (
            <div>Loading</div>
          ) : editMode ? (
            // Form Edit
            <div>
              <div className="mb-4">
                <label className="block text-gray-700">Title Book</label>
                <input
                  type="text"
                  name="bookTitle"
                  value={editData.bookTitle}
                  onChange={(e)=>handleInputChange(e)}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Author</label>
                <input
                  type="text"
                  name="author"
                  value={editData.author}
                  onChange={(e)=>handleInputChange(e)}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Stock Book</label>
                <input
                  type="number"
                  name="fill"
                  onChange={(e)=>handleInputChange(e)}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.stockBook}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Description</label>
                <textarea
                  name="description"
                  onChange={(e)=>handleInputChange(e)}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder="Description"
                />
              </div>
          
              <div className="mb-4">
                <label className="block text-gray-700">Bookshelf Category</label>
                <input
                  type="text"
                  name="categoryBook"
                  onChange={(e)=>handleInputChange(e)}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.bookshelf.categoryBook}
                />
              </div>
              <button
                onClick={handleUpdate}
                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              >
                Update
              </button>
              <button
                onClick={() => setEditMode(false)}
                className="ml-4 bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded"
              >
                Cancel
              </button>
            </div>
          ) : (
            // Tampilan Tabel
            <>
              <div className="flex justify-between items-center px-6 pb-4">
                <div className="flex items-center space-x-4">
                  <div>
                    <label className="mr-2 text-gray-600 ">Sort By:</label>
                    <select
                      value={orderBy}
                      className="px-4 py-2 border rounded-lg bg-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      onChange={(e) => {
                        handleChangeOrderBy(e);
                      }}
                    >
                      <option value="bookTitle">Title Book</option>
                      <option value="author">Author</option>
                      <option value="statusBook">Status Book</option>
                      <option value="bookshelf.categoryBook">Category Book</option>
                      <option value="stockBook">Stock Book</option>
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
                    Page {page}
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
                      <th className="py-3 px-2 text-left">Title Book</th>
                      <th className="py-3 px-1 text-left">Author</th>
                      <th className="py-3 px-1 text-left">Stock Book</th>
                      <th className="py-3 px-1 text-left">Status Book</th>
                      <th className="py-3 px-1 text-left">Category</th>
                      <th className="py-3 px-1 text-left">Recording Date</th>
                      <th className="py-3 px-1 text-left">Actions</th>
                    </tr>
                  </thead>
                  <tbody className="text-gray-600 text-sm font-medium">
                    {book.map((item, index) => (
                      <tr
                        key={index}
                        className="border-b-2 border-gray-300 hover:bg-slate-300"
                      >
                        <td className="py-3 px-2 text-left whitespace-nowrap">
                          <span className="font-bold">{item.bookTitle}</span>
                        </td>
                        <td className="py-3 px-1 text-left">{item.author}</td>
                        <td className="py-3 px-1 text-left">{item.stockBook}</td>
                        <td className="py-3 px-1 text-left">{item.statusBook}</td>
                        <td className="py-3 px-1 text-left">
                          {item.bookshelf.categoryBook}
                        </td>
                        <td className="py-3 px-1 text-left">
                          {new Date(item.recordingDate).toLocaleDateString()}
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

export default ViewBook;
