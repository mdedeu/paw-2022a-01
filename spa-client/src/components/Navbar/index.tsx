import './styles.css';

import icons from "../../assets";
import { Link } from 'react-router-dom';
import useUserService from '../../hooks/useUserService';
import { useState } from 'react';

const Navbar = () => {

    const userService = useUserService(); 

    const [username, setUsername] = useState<string | null>(userService.getLoggedInUser()); 

    return (
        <div className="flex w-full bg-white p-7">
            <nav className="flex flex-row">
                <Link  className="my-auto" to="/">
                    <img src={icons.logo} alt="cryptuki logo"/>
                </Link>
                <div className="flex flex-row my-auto mx-auto justify-between">
                    <Link to="/" className="hover:cursor-pointer justify-around">
                        <h1>Mercado P2P</h1>
                    </Link>
                    <Link to="/contact" className="hover:cursor-pointer">
                        <h1>Contáctate</h1>
                    </Link>
                    {
                        !username &&
                        <div >
                            <Link to="/login">
                                <button className="gray bold">Inicia sesión</button>
                            </Link>
                            <Link to="/register">
                                <button className="dark bold">Regístrate</button>
                            </Link>
                        </div>
                    }
                    {
                        username &&
                        <div>
                            <Link to="/seller">
                                <button className="gray bold">Panel de vendedor</button>
                            </Link>
                            <Link onClick={ () => {setUsername(userService.getLoggedInUser())}} to="/buyer">
                                <div>
                                    <img src={icons.profile} alt="profile icon"/>
                                    <p>{username}</p>
                                </div>
                            </Link>
                            <img onClick={() => {localStorage.removeItem("refreshToken"); localStorage.removeItem("accessToken"); setUsername(null); } } src={icons.logout} alt="logout"/>
                        </div>
                    }
                </div>
            </nav>
        </div>

    )

}; 

export default Navbar; 