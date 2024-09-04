import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8080"
});

const authoriz = {
    headers:{
        Authorization: 'Bearer ' + localStorage.getItem("token")
    }
  }

const authorizPhoto =  {
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
    "Content-Type": "Multipart/form-data",
  },
}


// Login
export async function login(login){
    try{
        const response = await api.post("/auth/login",login);
        return response.data.data;
    }catch (error){
        console.error(error);
        return null;
    }
}

// Forgot Password (to get Token for reset password)
export async function forgotPassword(email){
    try{
        const response = await api.post("/forgot-password/get-token?email=" + email);
        return response.data.data;
    }
    catch (error){
        console.log(error);
        return null;
    }
}

// Reset Password
export async function resetPassword(token,newPass){
    try {
        const response = await api.post("/forgot-password/reset?token=" + token + "&newPassword=" + newPass);
        return response.data.data;
    } catch (error) {
        console.log(error);
        return null;
    }
}

// Register Officer
export async function registerOfficer(registration) {
    try {
      const response = await api.post("/officer/register-officer", registration, authoriz);
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting officer:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
        throw new Error(`Officer registration error : ${error.message}`);
        }
    }
  }
// Remove Officer
export async function removeOfficer(id) {
  try {
    const response = await api.delete("/officer/remove-officer?id=" + id,authoriz);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 500) {
        // console.error("Error submitting book:", error.response.data.message)            
        // Menampilkan pesan error ke user
        throw error.response.data.message;
    } 
    else {
      console.log(error);
      throw "Cannot delete this field because it is linked to another table.";
  // throw new Error(`Book registration error : ${error.message}`);
    }
}
}

// Update Officer
export async function updateOfficer(id,newOfficer) {
  try {
    const response = await api.put("/officer/update-officer?id=" + id, newOfficer, authoriz );
    return response.data;
  } catch (error) {
      if (error.response && error.response.status === 500) {
          console.error("Error submitting bookshelf:", error.response.data.message)            
          // Menampilkan pesan error ke user
          throw error.response.data.message;
      } 
      else {
        throw error.code;
      // throw new Error(`Bookshelf registration error : ${error.message}`);
      }
  }
}

// View Officer 
export async function viewOfficer(page,size,sortBy,orderBy) {
  try {
    const response = await api.get(`/officer/find-all-officer?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${orderBy}`, authoriz );
    return response.data.data.items;
  } catch (error) {
      if (error.response && error.response.status === 500) {
          console.error("Error viewing Officer:", error.response.data.message)            
          // Menampilkan pesan error ke user
          throw error.response.data.message;
      } 
      else {
          console.log("Masuk else")
          throw error
          // throw new Error(`Access Denied`);
        }
  }
}


// Register Member
export async function registerMember(registration) {
    try {
      const response = await api.post("/member/register-member", registration, authoriz);
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting member:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
        throw new Error(`Member registration error : ${error.message}`);
        }
    }
  }

  // Remove Member
  export async function removeMember(id) {
    try {
      const response = await api.delete("/member/remove-member?id=" + id,authoriz);
      return response.data;
    } catch (error) {
      if (error.response && error.response.status === 500) {
          // console.error("Error submitting book:", error.response.data.message)            
          // Menampilkan pesan error ke user
          throw error.response.data.message;
      } 
      else {
        console.log(error);
        throw "Cannot delete this field because it is linked to another table.";
      // throw new Error(`Book registration error : ${error.message}`);
      }
  }
  }


  // Update Member
  export async function updateMember(id,newMember) {
    try {
      const response = await api.put("/member/update-member?id=" + id, newMember, authoriz );
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting bookshelf:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }


  // View Member
  export async function viewMember(page,size,sortBy,orderBy) {
    try {
      const response = await api.get(`/member/find-all-member?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${orderBy}`, authorizPhoto );
      return response.data.data.items;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error viewing Member:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
            console.log("Masuk else")
            throw new Error(`Access Denied`);
          }
    }
  }

  // Add Bookshelf
  export async function addBookshelf(newBookshelf) {
    try {
      const response = await api.post("/book/add-bookshelf", newBookshelf, authoriz );
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting bookshelf:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }

  // Remove Bookshelf
  export async function removeBookshelf(id) {
    try {
      const response = await api.delete("/book/remove-bookshelf?id=" + id,authoriz);
      console.log(response);
      return response.data;
    } catch (error) {
      if (error.response && error.response.status === 500) {
          // console.error("Error submitting book:", error.response.data.message)            
          // Menampilkan pesan error ke user
          throw error.response.data.message;
      } 
      else {
        console.log(error)
        throw error.code;
      // throw new Error(`Book registration error : ${error.message}`);
      }
  }
  }


  // Update Bookshelf
  export async function updateBookshelf(id,newBookshelf) {
    try {
      const response = await api.put("/book/update-bookshelf?id=" + id, newBookshelf, authoriz );
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting bookshelf:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }



  // View Bookshelf
  export async function viewBookshelf(page,size,sortBy,orderBy) {
    try {
      const response = await api.get(`/book/find-bookshelf?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${orderBy}`, authoriz );
      return response.data.data.items;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error viewing bookshelf:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
            console.log("Masuk else")
        throw new Error(`Access Denied`);
        }
    }
  }



  // Add Book
  export async function addBook(newBook) {
    try {
      const response = await api.post("/book/add-book", newBook, authoriz);
      
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting book:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Book registration error : ${error.message}`);
        }
    }
  }


  // Remove Book 
  export async function removeBook(id) {
    try {
      const response = await api.delete("/book/remove-book?id=" + id,authoriz);
      return response.data;
    } catch (error) {
      if (error.response && error.response.status === 500) {
          // console.error("Error submitting book:", error.response.data.message)            
          // Menampilkan pesan error ke user
          throw error.response.data.message;
      } 
      else {
        console.log(error);
        throw "Cannot delete this field because it is linked to another table.";
      // throw new Error(`Book registration error : ${error.message}`);
      }
    }
  }
  // Update Book
  export async function updateBook(id,newBook) {
    try {
      const response = await api.put("/book/update-book?id=" + id, newBook, authoriz );
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting bookshelf:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }


  // View Book 
  export async function viewBook(page,size,sortBy,orderBy) {
    try {
      const response = await api.get(`/book/find-all-book?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${orderBy}`, authoriz );
      return response.data.data.items;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error viewing book:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
            console.log("Masuk else")
            throw new Error(`Access Denied`);
          }
    }
  }

  // Record Book (Borrowing Book)
  export async function recordBook(record) {
    try {
      const response = await api.post("/officer/record-book", record, authoriz);
      
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting book:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Book registration error : ${error.message}`);
        }
    }
  }

  // Return Book
  export async function returnBook(record) {
    try {
      const response = await api.post("/officer/return-book", record, authoriz);
      
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting book:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
          throw error.code;
        // throw new Error(`Book registration error : ${error.message}`);
        }
    }
  }

  // View Borrowed
  export async function viewBorrowing(page,size,sortBy,orderBy) {
    try {
      const response = await api.get(`/member/view-all-borrowing?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${orderBy}`, authorizPhoto );
      return response.data.data.items;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error viewing :", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
            console.log("Masuk else")
            throw new Error(`Access Denied`);
          }
    }
  }





