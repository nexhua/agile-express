import BoardPage from "../pages/BoardPage";
import Dashboard from "../pages/DashboardPage";
import ProjectPage from "../pages/ProjectPage";

const routes = [
  {
    path: "/",
    component: Dashboard,
  },
  {
    path: "/dashboard",
    component: Dashboard,
  },
  {
    path: "/project",
    component: ProjectPage,
  },
  {
    path: "/board",
    component: BoardPage,
  },
];

export default routes;
