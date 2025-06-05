import React, { useCallback, useEffect, useState } from 'react'

const Login = () => {
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

    return (
        <div className='w-full h-screen bg-slate-100 flex justify-center items-center p-10'>
            <form action="" method="post" className='bg-white p-10 rounded-md shadow-2xl/20 border-gray-200 max-w-[400px] w-full'>
                <h1 className='text-4xl text-center font-semibold'>LoyalBridge</h1>

                <div className='mt-10 flex flex-col gap-3'>
                    <div>
                        <label htmlFor="email" className='text-sm font-semibold'>Email</label>
                        <input type="email" name="email" id="email" value={values?.email} onChange={handleChange} placeholder='Email' className='w-full text-gray-700 border border-gray-300 rounded-md outline-none py-2 px-4' />
                    </div>
                    <div>
                        <label htmlFor="password" className='text-sm font-semibold'>Password</label>
                        <input type="password" name="password" id="password" value={values?.password} onChange={handleChange} placeholder='Password' className='w-full text-gray-700 border border-gray-300 rounded-md outline-none py-2 px-4' />
                    </div>
                </div>

                <button className='w-full bg-black text-white font-semibold mt-14 shadow-2xl p-3 rounded-md hover:-translate-y-1 transition-all duration-200 cursor-pointer'>Login</button>
            </form>
        </div>
    )
}

export default Login