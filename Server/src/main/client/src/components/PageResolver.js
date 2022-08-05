import Dashboard from "../pages/DashboardPage";
import Hello from "../pages/HelloPage";
import ProjectPage from "../pages/ProjectPage";
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
  {
    path: "/project",
    component: ProjectPage,
  },
];

export default routes;
