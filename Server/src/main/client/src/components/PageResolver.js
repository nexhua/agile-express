import Dashboard from "../pages/DashboardPage";
import Hello from "../pages/HelloPage";
import User from "../pages/UserPage";

const routes = [
  {
    path: "/",
    component: User,
  },
  {
    path: "/hello",
    component: Hello,
  },
  {
    path: "/user",
    component: User,
  },
  {
    path: "/dashboard",
    component: Dashboard,
  },
];

export default routes;
