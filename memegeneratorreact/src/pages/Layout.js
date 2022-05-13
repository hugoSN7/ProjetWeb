import { Outlet, Link } from "react-router-dom";
import '/home/ternardin/Documents/2A/ProjetWeb/memegeneratorreact/src/Design.css';

const Layout = () => {
  return (
    <>
      <h1 class="titre">MemeGenerator </h1>
      <nav>
        <ul>
          <li>
            <Link to="/home" class="urlHome">Home</Link>
          </li>
          <li>
            <Link to="/" class="urlGenerate">Generate</Link>
          </li>
          <li>
            <Link to="/login" class="urlLogin">Login</Link>
          </li>
          <li>
            <Link to="/about" class="urlAbout">About</Link>
          </li>
          <li>
            <Link to="/contact" class="urlContact">Contact</Link>
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Layout;
