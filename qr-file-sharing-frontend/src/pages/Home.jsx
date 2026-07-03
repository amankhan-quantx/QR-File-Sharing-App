import Navbar from "../components/layout/Navbar";

function Home() {
  return (
    <>
      <Navbar />

      <div style={{ padding: "30px" }}>
        <h1>Welcome</h1>

        <p>
          Upload, download and share files using QR Codes.
        </p>
      </div>
    </>
  );
}

export default Home;