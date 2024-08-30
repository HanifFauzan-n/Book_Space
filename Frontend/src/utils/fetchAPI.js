import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8080"
});

const authoriz = {
    headers:{
        Authorization: 'Bearer ' + localStorage.getItem("token")
    }
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

// Register Member
export async function registerMember(registration) {
    try {
      const response = await api.post("/member/register-member", registration, authoriz);
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting book:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
        throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }

  // Remove Member


  // Update Member


  // View Member

  // Add Bookshelf
  export async function addBookshelf(newBookshelf) {
    try {
      const response = await api.post("/book/add-bookshelf", newBookshelf, authoriz);
      return response.data;
    } catch (error) {
        if (error.response && error.response.status === 500) {
            console.error("Error submitting book:", error.response.data.message)            
            // Menampilkan pesan error ke user
            throw error.response.data.message;
        } 
        else {
        throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }

  // Remove Bookshelf


  // Update Bookshelf


  // View Bookshelf


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
        throw new Error(`Bookshelf registration error : ${error.message}`);
        }
    }
  }


