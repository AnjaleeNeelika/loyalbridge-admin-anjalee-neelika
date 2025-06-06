import React, { useCallback, useEffect, useState } from 'react'
import { data, redirect, useNavigate } from 'react-router-dom';

const Login = () => {
    const [error, setError] = useState();
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    // input values
    const [values, setValues] = useState(
        {
            "email": "",
            "password": "",
        }
    );

    const handleChange = useCallback((e) => {
        setValues(values => ({
            ...values,
            [e.target.name]: e.target?.value,
        }))
    }, []);

    useEffect(() => {
        console.log('Updated values:', values);
    }, [values]);


    const handleSubmit = async (e) =>{
        e.preventDefault();
        setLoading(true);
        setError('');

        const emailRegex = /^[^\s@]+@loyalbridge\.io$/;
        const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{12,}$/;

        if (!emailRegex.test(values.email)) {
            setError("Email must be @loyalbridge.io");
            setLoading(false);
            return;
        }

        if (!passwordRegex.test(values.password)) {
            setError("Password must be at least 12 characters with 1 uppercase and 1 special character.");
            setLoading(false);
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(values),
            });

            const data = await response.json();

            // Login unsuccessful
            if (!response.ok) {
                setError(data.token || 'Login failed');
                setLoading(false);
                return;
            }

            // Successful login
            if (data.token) {
                // Save the token in the local storage
                localStorage.setItem('token', data.token);
                console.log(localStorage.getItem('token'));
                console.log('Login successful', data);
                setValues({ email: '', password: '' });
                navigate('/');
            }

            setLoading(false);
        } catch (err) {
            setError('Network error: ' + err.message);
            setLoading(false);
        }
    }

    return (
        <div className='w-full h-screen bg-slate-100 flex justify-center items-center p-10'>
            <form onSubmit={handleSubmit} action="" method="post" className='bg-white p-10 rounded-md shadow-2xl/20 border-gray-200 max-w-[400px] w-full'>
                <h1 className='text-4xl text-center font-semibold'>LoyalBridge</h1>

                <div className='mt-10 flex flex-col gap-3'>
                    {error && <p className='text-red-600'>{error}</p>}
                    <div>
                        <label htmlFor="email" className='text-sm font-semibold'>Email</label>
                        <input type="email" name="email" id="email" value={values?.email} onChange={handleChange} placeholder='Email' className='w-full text-gray-700 border border-gray-300 rounded-md outline-none py-2 px-4' required />
                    </div>
                    <div>
                        <label htmlFor="password" className='text-sm font-semibold'>Password</label>
                        <input type="password" name="password" id="password" value={values?.password} onChange={handleChange} placeholder='Password' className='w-full text-gray-700 border border-gray-300 rounded-md outline-none py-2 px-4' required />
                    </div>
                </div>

                <button 
                    type='submit'
                    disabled={loading}
                    className='w-full bg-black text-white font-semibold mt-14 shadow-2xl p-3 rounded-md hover:-translate-y-1 transition-all duration-200 cursor-pointer'
                >
                    {loading ? 'Logging in...' : 'Login'}
                </button>
            </form>
        </div>
    )
}

export default Login