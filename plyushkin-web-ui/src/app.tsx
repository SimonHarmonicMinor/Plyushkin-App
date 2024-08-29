import React from "react";
import "./App.css";
import { Layout, Menu } from "antd";

const { Header, Content } = Layout;

const items = Array.from({ length: 15 })
  .fill("")
  .map((_, index) => ({
    key: index + 1,
    label: `nav ${index + 1}`,
  }));

function App({ children }: { children?: React.ReactNode }) {
  return (
    <Layout>
      <Header style={{ display: "flex", alignItems: "center" }}>
        <div className="demo-logo" />
        <Menu
          theme="dark"
          mode="horizontal"
          defaultSelectedKeys={["2"]}
          items={items}
          style={{ flex: 1, minWidth: 0 }}
        />
      </Header>
      <Content style={{ padding: "0 48px" }}>
        <div
          style={{
            minHeight: 280,
            padding: 24,
          }}
        >
          {children}
        </div>
      </Content>
    </Layout>
  );
}

export default App;
