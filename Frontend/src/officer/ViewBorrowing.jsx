import { useEffect } from "react";
import { useState } from "react";
import { viewBorrowing } from "../utils/fetchAPI";
import Sidebar from "../components/Sidebar";
import { ArrowLeft } from "lucide-react";
import { ArrowRight } from "lucide-react";

const ViewBorrowing = () => {
  const [view, setView] = useState([
    {
      member: {},
      book: {},
      description: "",
      loanDate: "",
      returnDate: "",
    },
  ]);
  const [page, setPage] = useState(1);
  const [orderBy, setOrderBy] = useState("member");
  const [sortOrder, setSortOrder] = useState("desc");
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleChangeSort = (e) => {
    let value = e.target.value;

    setSortOrder(value);
  };

  const handlePageRight = () => {
    if (view.length === 0) {
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

  const fetchView = async () => {
    setIsLoading(true);
    try {
      const result = await viewBorrowing(page, 5, orderBy, sortOrder);
      console.log(result);
      setSuccessMessage("Table Information");
      setView(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchView();
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

          <h2 className="text-2xl font-bold text-gray-800 p-6">View Record Book</h2>
          {isLoading ? (
            <div>Loading</div>
          ) : (
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
                      <option value="member">Name</option>
                      <option value="book">book</option>
                      <option value="description">Description</option>
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
                      <th className="py-3 px-1 text-left">Borrower name</th>
                      <th className="py-3 px-1 text-center">Book Title</th>
                      <th className="py-3 px-1 text-left">Author</th>
                      <th className="py-3 px-1 text-left">Description</th>
                      <th className="py-3 px-1 text-left">Borrowed Date</th>
                      <th className="py-3 px-1 text-left">Return Date</th>
                    </tr>
                  </thead>
                  <tbody className="text-gray-600 text-sm font-medium">
                    {view.map((item, index) => (
                      <tr
                        key={index}
                        className="border-b-2 border-gray-300 hover:bg-slate-300"
                      >
                        <td className="py-3 px-2 text-left whitespace-nowrap">
                          <span className="font-bold">{item.member.memberName}</span>
                        </td>
                        <td className="py-3 px-1 text-left">{item.book.bookTitle}</td>
                        <td className="py-3 px-1 text-left">{item.book.author}</td>
                        <td className="py-3 px-1 text-left">{item.description}</td>
                        <td className="py-3 px-1 text-left">{item.loanDate}</td>
                        <td className="py-3 px-1 text-left">{item.returnDate}</td>
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

export default ViewBorrowing;
