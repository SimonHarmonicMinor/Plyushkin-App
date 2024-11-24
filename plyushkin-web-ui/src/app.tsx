import React from "react";
import "./App.css";
import { Layout } from "antd";
import { RocketOutlined } from "@ant-design/icons";

const { Header, Content } = Layout;

function App({ children }: { children?: React.ReactNode }) {
  return (
    <Layout>
      <Header
        style={{
          display: "flex",
          alignItems: "center",
          backgroundColor: "gray",
        }}
      >
        <RocketOutlined />
        Plyushkin Budget Application
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
