import Dashboard from "../pages/DashboardPage";
import Hello from "../pages/HelloPage";
import User from "../pages/UserPage";
import ProjectDetail from "../pages/ProjectDetail";

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
    path: "/detail/:projectID",
    component: ProjectDetail,
  },
];

export default routes;
