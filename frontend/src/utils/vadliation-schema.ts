import * as yup from 'yup';
const VietnameseNameRegex = /^([A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯ][a-zàáâãèéêìíòóôõùúăđĩũơưạ-ỹ]*)( [A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯ][a-zàáâãèéêìíòóôõùúăđĩũơưạ-ỹ]*)*$/;



export const userRegistrationValidation = yup.object({
  username: yup.string().required('Username is required').trim(),
  email: yup.string().email('Invalid email format').required('Email is required').trim(),
  firstName: yup.string().matches(VietnameseNameRegex
    , 'First letter must be capitalized').required('Firstname is required').trim(),
  lastName: yup.string().matches(VietnameseNameRegex
    , 'First letter must be capitalized').required('Lastname is required').trim(),
  password: yup.string().min(5, 'Password must be at least 5 characters').max(15, 'Password must be at most 15 characters').required('Password is required'),
  confirmPassword: yup.string().oneOf([yup.ref('password')], 'Confirm password must match password').required('Confirm password is required'),
  profilePicture: yup.string().nullable(),
  provider: yup.string().nullable(),
});

export const userLoginValidation = yup.object({
  username: yup.string().required('Username is required').trim(),
  password: yup.string().min(5, 'Password must be at least 5 characters').max(15, 'Password must be at most 15 characters').required('Password is required'),
});

export const userUpdateValidation = yup.object().shape({
  username: yup
    .string()
    .required('Username must not be blank!')
    .nullable(),
  firstName: yup
    .string()
    .required('First name must not be blank!')
    .matches(VietnameseNameRegex
      , 'First letter of first name must be capitalized!'),
  lastName: yup
    .string()
    .required('Last name must not be blank!')
    .matches(VietnameseNameRegex
      , 'First letter of last name must be capitalized!'),
});
