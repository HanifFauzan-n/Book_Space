/* eslint-disable react-refresh/only-export-components */
/* eslint-disable react/prop-types */
import { createContext, useState, useContext } from "react";

// Membuat Context
const ContentContext = createContext();

// Membuat Provider
export const ContentProvider = ({ children }) => {
  const [contentType, setContentType] = useState("book");

  const field = [
    {
      book: {
        name: "book",
        link: ["/create-book", "/read-book", "/update-book", "/delete-book"],
      },
    },
    {
      member: {
        name: "member",
        link: ["/create-member", "/read-member", "/update-member", "/delete-member"],
      },
    },
    {
      bookshelf: {
        name: "bookshelf",
        link: ["/create-bookshelf", "/read-bookshelf", "/update-bookshelf", "/delete-bookshelf"],
      },
    },
  ];

  return (
    <ContentContext.Provider value={{ contentType, setContentType, field }}>
      {children}
    </ContentContext.Provider>
  );
};

// Custom hook untuk menggunakan Context
export const useContent = () => useContext(ContentContext);
