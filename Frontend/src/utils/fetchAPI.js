import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8080"
});

export async function login(login){
    try{
        const response = await api.post("/auth/login",login);
        return response.data.data;
    }catch (error){
        console.error(error);
        return null;
    }
}

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

export async function resetPassword(token,newPass){
    try {
        const response = await api.post("/forgot-password/reset?token=" + token + "&newPassword=" + newPass);
        return response.data.data;
    } catch (error) {
        console.log(error);
        return null;
    }
}